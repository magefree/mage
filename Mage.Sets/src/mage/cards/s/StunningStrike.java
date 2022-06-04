package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StunningStrike extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    private static final Condition condition = new AttachedToMatchesFilterCondition(filter);

    public StunningStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // When Stunning Strike enters the battlefield, tap enchanted creature and remove it from combat.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new StunningStrikeEffect()));

        // As long as enchanted creature isn't legendary, it doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new DontUntapInControllersUntapStepEnchantedEffect(), condition, "as long as " +
                "enchanted creature isn't legendary, it doesn't untap during its controller's untap step"
        )));
    }

    private StunningStrike(final StunningStrike card) {
        super(card);
    }

    @Override
    public StunningStrike copy() {
        return new StunningStrike(this);
    }
}

class StunningStrikeEffect extends OneShotEffect {

    StunningStrikeEffect() {
        super(Outcome.Benefit);
        staticText = "tap enchanted creature and remove it from combat";
    }

    private StunningStrikeEffect(final StunningStrikeEffect effect) {
        super(effect);
    }

    @Override
    public StunningStrikeEffect copy() {
        return new StunningStrikeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.of(source.getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .ifPresent(permanent -> {
                    permanent.tap(source, game);
                    permanent.removeFromCombat(game);
                });
        return true;
    }
}
