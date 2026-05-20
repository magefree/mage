package mage.cards.s;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.*;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SocialSnub extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(new FilterControlledCreaturePermanent("you control a creature"));

    public SocialSnub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{B}");

        // When you cast this spell while you control a creature, you may copy this spell.
        this.addAbility(new CastSourceTriggeredAbility(new CopySourceSpellEffect(), true)
                .withTriggerCondition(condition).addHint(CreaturesYouControlHint.instance));

        // Each player sacrifices a creature of their choice. Each opponent loses 1 life and you gain 1 life.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE));
        this.getSpellAbility().addEffect(new LoseLifeOpponentsEffect(1));
        this.getSpellAbility().addEffect(new GainLifeEffect(1).concatBy("and"));
    }

    private SocialSnub(final SocialSnub card) {
        super(card);
    }

    @Override
    public SocialSnub copy() {
        return new SocialSnub(this);
    }
}
