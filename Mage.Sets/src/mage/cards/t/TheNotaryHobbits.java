package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class TheNotaryHobbits extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("it's not a token");
    static { filter.add(TokenPredicate.FALSE); }
    private static final Condition condition = new SourceMatchesFilterCondition(filter);

    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent(SubType.HALFLING);
    private static final Hint hint = new ValueHint("Halflings you control", new PermanentsOnBattlefieldCount(filter2));

    public TheNotaryHobbits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When The Notary Hobbits enter, if they're not a token, create two tokens that are copies of them, except the tokens aren't legendary.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TheNotaryHobbitsEffect()).withInterveningIf(condition));

        // {T}: Add {C} for each Halfling you control.
        this.addAbility(new DynamicManaAbility(
            Mana.ColorlessMana(1),
            new PermanentsOnBattlefieldCount(filter2),
            new TapSourceCost()
        ).addHint(hint));
    }

    private TheNotaryHobbits(final TheNotaryHobbits card) {
        super(card);
    }

    @Override
    public TheNotaryHobbits copy() {
        return new TheNotaryHobbits(this);
    }
}

class TheNotaryHobbitsEffect extends OneShotEffect {

    TheNotaryHobbitsEffect() {
        super(Outcome.Benefit);
        staticText = "create two tokens that are copies of them, except the tokens aren't legendary";
    }

    private TheNotaryHobbitsEffect(final TheNotaryHobbitsEffect effect) {
        super(effect);
    }

    @Override
    public TheNotaryHobbitsEffect copy() {
        return new TheNotaryHobbitsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentEnteredBattlefield");
        if (permanent == null) {
            return false;
        }

        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
            source.getControllerId(), null, false, 2
        ).setSavedPermanent(permanent).setIsntLegendary(true);
        effect.apply(game, source);

        return true;
    }
}
