package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalPreventionEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrodoDeterminedHero extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("Equipment you control with mana value 2 or 3");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 1));
    }

    public FrodoDeterminedHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Frodo, Determined Hero enters the battlefield or attacks, you may attach target Equipment you control with mana value 2 or 3 to Frodo.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new FrodoDeterminedHeroEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // As long as it's your turn, prevent all damage that would be dealt to Frodo.
        this.addAbility(new SimpleStaticAbility(new ConditionalPreventionEffect(
                new PreventAllDamageToSourceEffect(Duration.WhileOnBattlefield), MyTurnCondition.instance,
                "as long as it's your turn, prevent all damage that would be dealt to {this}"
        )));
    }

    private FrodoDeterminedHero(final FrodoDeterminedHero card) {
        super(card);
    }

    @Override
    public FrodoDeterminedHero copy() {
        return new FrodoDeterminedHero(this);
    }
}

class FrodoDeterminedHeroEffect extends OneShotEffect {

    FrodoDeterminedHeroEffect() {
        super(Outcome.Benefit);
        staticText = "attach target Equipment you control with mana value 2 or 3 to {this}";
    }

    private FrodoDeterminedHeroEffect(final FrodoDeterminedHeroEffect effect) {
        super(effect);
    }

    @Override
    public FrodoDeterminedHeroEffect copy() {
        return new FrodoDeterminedHeroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && equipment != null
                && permanent.addAttachment(equipment.getId(), source, game);
    }
}
