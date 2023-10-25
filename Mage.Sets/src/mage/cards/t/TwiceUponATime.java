package mage.cards.t;

import mage.abilities.common.CastOnlyIfConditionIsTrueAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwiceUponATime extends AdventureCard {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DOCTOR, "you control two or more Doctors");
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1);
    private static final Hint hint = new ConditionHint(condition, "You control two or more Doctors");
    private static final FilterCard filter2 = new FilterCard("a Doctor card");

    static {
        filter2.add(SubType.DOCTOR.getPredicate());
    }

    public TwiceUponATime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{4}{U}{U}", "Unlikely Meeting", "{2}{U}");

        // Cast this spell only if you control two or more Doctors.
        this.addAbility(new CastOnlyIfConditionIsTrueAbility(condition).addHint(hint));

        // Take an extra turn after this one. Exile Twice Upon a Time.
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());

        // Unlikely Meeting
        // Search your library for a Doctor card, reveal it, put it into your hand, then shuffle.
        this.getSpellCard().getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter2), true));
        this.getSpellCard().finalizeAdventure();
    }

    private TwiceUponATime(final TwiceUponATime card) {
        super(card);
    }

    @Override
    public TwiceUponATime copy() {
        return new TwiceUponATime(this);
    }
}
