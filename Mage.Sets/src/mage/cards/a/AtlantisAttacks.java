package mage.cards.a;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.TeamworkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.LeviathanToken;
import mage.target.TargetPlayer;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author muz
 */
public final class AtlantisAttacks extends CardImpl {

    public AtlantisAttacks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}{U}");

        // Teamwork 4
        this.addAbility(new TeamworkAbility(4));

        // Choose one. If this spell was cast using teamwork, choose both instead.
        this.getSpellAbility().getModes().setChooseText(
            "Choose one. If this spell was cast using teamwork, choose both instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(2, TeamworkCondition.instance);

        // * Target player creates a 6/5 blue Leviathan creature token with hexproof.
        this.getSpellAbility().addEffect(new CreateTokenTargetEffect(new LeviathanToken()));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // * Return one or two target nonland permanents to their owners' hands.
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetNonlandPermanent(1, 2, false));
        this.getSpellAbility().addMode(mode);
    }

    private AtlantisAttacks(final AtlantisAttacks card) {
        super(card);
    }

    @Override
    public AtlantisAttacks copy() {
        return new AtlantisAttacks(this);
    }
}
