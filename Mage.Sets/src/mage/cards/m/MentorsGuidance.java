package mage.cards.m;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MentorsGuidance extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.or(
                CardType.PLANESWALKER.getPredicate(),
                SubType.CLERIC.getPredicate(),
                SubType.DRUID.getPredicate(),
                SubType.SHAMAN.getPredicate(),
                SubType.WARLOCK.getPredicate(),
                SubType.WIZARD.getPredicate()
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public MentorsGuidance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // When you cast this spell, copy it if you control a planeswalker, Cleric, Druid, Shaman, Warlock, or Wizard.
        this.addAbility(new CastSourceTriggeredAbility(new ConditionalOneShotEffect(
                new CopySourceSpellEffect(), condition, "copy it if you control " +
                "a planeswalker, Cleric, Druid, Shaman, Warlock, or Wizard")));

        // Scry 1, then draw a card.
        this.getSpellAbility().addEffect(new ScryEffect(1, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
    }

    private MentorsGuidance(final MentorsGuidance card) {
        super(card);
    }

    @Override
    public MentorsGuidance copy() {
        return new MentorsGuidance(this);
    }
}
