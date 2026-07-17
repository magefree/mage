package mage.cards.s;

import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SerpentineAmbush extends CardImpl {

    public SerpentineAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Until end of turn, target creature becomes a blue Serpent with base power and toughness 5/5.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(
                        5, 5, "blue Serpent with base power and toughness 5/5"
                ).withColor("U").withSubType(SubType.SERPENT),
                false, false, Duration.EndOfTurn
        ).withDurationRuleAtStart(true).setRemoveSubtypes(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SerpentineAmbush(final SerpentineAmbush card) {
        super(card);
    }

    @Override
    public SerpentineAmbush copy() {
        return new SerpentineAmbush(this);
    }
}
