package mage.cards.h;

import java.util.UUID;

import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TeamworkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author muz
 */
public final class HeroicTeamwork extends CardImpl {

    public HeroicTeamwork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Teamwork 3
        this.addAbility(new TeamworkAbility(3));

        // One or two target creatures each get +2/+1 until end of turn. If this spell was cast using teamwork, draw a card.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1, 2));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new DrawCardSourceControllerEffect(1),
            TeamworkCondition.instance,
            "If this spell was cast using teamwork, draw a card"
        ));
    }

    private HeroicTeamwork(final HeroicTeamwork card) {
        super(card);
    }

    @Override
    public HeroicTeamwork copy() {
        return new HeroicTeamwork(this);
    }
}
