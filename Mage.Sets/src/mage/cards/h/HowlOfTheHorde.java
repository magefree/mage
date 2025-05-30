package mage.cards.h;

import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class HowlOfTheHorde extends CardImpl {

    public HowlOfTheHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");


        // When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy. 
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()));

        // <i>Raid</i> &mdash; If you attacked with a creature this turn, when you cast your next instant or sorcery spell this turn, copy that spell an additional time. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()),
                RaidCondition.instance,
                "<br><br><i>Raid</i> &mdash; If you attacked this turn, when you next cast an instant or sorcery spell this turn, copy that spell an additional time. You may choose new targets for the copy.")
        );
        this.getSpellAbility().addWatcher(new PlayerAttackedWatcher());
        this.getSpellAbility().addHint(RaidHint.instance);
    }

    private HowlOfTheHorde(final HowlOfTheHorde card) {
        super(card);
    }

    @Override
    public HowlOfTheHorde copy() {
        return new HowlOfTheHorde(this);
    }
}
