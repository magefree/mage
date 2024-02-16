package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WintersRest extends CardImpl {

    public WintersRest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Winter's Rest enters the battlefield, tap enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect()));

        // As long as you control another snow permanent, enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(new WintersRestEffect()));
    }

    private WintersRest(final WintersRest card) {
        super(card);
    }

    @Override
    public WintersRest copy() {
        return new WintersRest(this);
    }
}

class WintersRestEffect extends DontUntapInControllersUntapStepEnchantedEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SuperType.SNOW.getPredicate());
    }

    WintersRestEffect() {
        super();
        staticText = "As long as you control another snow permanent, " +
                "enchanted creature doesn't untap during its controller's untap step.";
    }

    private WintersRestEffect(final WintersRestEffect effect) {
        super(effect);
    }

    @Override
    public WintersRestEffect copy() {
        return new WintersRestEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        ).isEmpty()) {
            return false;
        }
        return super.applies(event, source, game);
    }
}
