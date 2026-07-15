package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class WinterSoldierIcyAssassin extends CardImpl {

    public WinterSoldierIcyAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Winter Soldier gets +2/+0 for each Equipment attached to him.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
            new EquipmentAttachedCount(2), StaticValue.get(0), Duration.WhileOnBattlefield
        ).setText("Winter Soldier gets +2/+0 for each Equipment attached to him")));

        // {3}{W}{B}: Return this card from your graveyard to the battlefield with a finality counter on him.
        // Then you may attach an Equipment you control to him.
        Ability ability = new SimpleActivatedAbility(
            Zone.GRAVEYARD,
            new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(
                CounterType.FINALITY.createInstance(), false
            ).setText("return this card from your graveyard to the battlefield with a finality counter on him"),
            new ManaCostsImpl<>("{3}{W}{B}")
        );
        ability.addEffect(new WinterSoldierIcyAssassinAttachEffect().concatBy("Then"));
        this.addAbility(ability);
    }

    private WinterSoldierIcyAssassin(final WinterSoldierIcyAssassin card) {
        super(card);
    }

    @Override
    public WinterSoldierIcyAssassin copy() {
        return new WinterSoldierIcyAssassin(this);
    }
}

class WinterSoldierIcyAssassinAttachEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter
        = new FilterControlledPermanent(SubType.EQUIPMENT);

    WinterSoldierIcyAssassinAttachEffect() {
        super(Outcome.Benefit);
        staticText = "you may attach an Equipment you control to him";
    }

    private WinterSoldierIcyAssassinAttachEffect(final WinterSoldierIcyAssassinAttachEffect effect) {
        super(effect);
    }

    @Override
    public WinterSoldierIcyAssassinAttachEffect copy() {
        return new WinterSoldierIcyAssassinAttachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || sourcePermanent == null) {
            return false;
        }
        TargetControlledPermanent target = new TargetControlledPermanent(0, 1, filter, true);
        target.withChooseHint("to attach to Winter Soldier");
        if (!player.choose(outcome, target, source, game)) {
            return false;
        }
        Permanent equipment = game.getPermanent(target.getFirstTarget());
        return equipment != null && sourcePermanent.addAttachment(equipment.getId(), source, game);
    }
}
