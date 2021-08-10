package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksEnchantedTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author zeffirojoe
 */
public final class WandOfOrcus extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombies you control");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public WandOfOrcus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ARTIFACT }, "{2}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks or blocks, it and Zombies you control gain
        // deathtouch until end of turn.
        Ability deathTouchAbility = new AttacksOrBlocksEnchantedTriggeredAbility(Zone.BATTLEFIELD,
                new GainAbilityAttachedEffect(DeathtouchAbility.getInstance(), AttachmentType.EQUIPMENT,
                        Duration.EndOfTurn));
        deathTouchAbility.addEffect(
                new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn, filter));
        this.addAbility(deathTouchAbility);

        // Whenever equipped creature deals combat damage to a player, create that many
        // 2/2 black Zombie creature tokens.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(new WandOfOrcusZombieEffect(), "equipped",
                false, true));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new ManaCostsImpl<>("{3}")));
    }

    private WandOfOrcus(final WandOfOrcus card) {
        super(card);
    }

    @Override
    public WandOfOrcus copy() {
        return new WandOfOrcus(this);
    }
}

class WandOfOrcusZombieEffect extends OneShotEffect {

    public WandOfOrcusZombieEffect() {
        super(Outcome.Benefit);
        this.staticText = "create that many 2/2 black Zombie creature tokens";
    }

    public WandOfOrcusZombieEffect(final WandOfOrcusZombieEffect effect) {
        super(effect);
    }

    @Override
    public WandOfOrcusZombieEffect copy() {
        return new WandOfOrcusZombieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damage = (Integer) this.getValue("damage");
        if (damage != null) {
            return new ZombieToken().putOntoBattlefield(damage, game, source, source.getControllerId());
        }
        return false;
    }
}
