package mage.cards.m;

import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonumentalCorruption extends CardImpl {

    public MonumentalCorruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Target player draws X cards an loses X life, where X is the number of artifacts you control.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(ArtifactYouControlCount.instance)
                .setText("target player draws X cards"));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(ArtifactYouControlCount.instance)
                .setText("and loses X life, where X is the number of artifacts you control"));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addHint(ArtifactYouControlHint.instance);
    }

    private MonumentalCorruption(final MonumentalCorruption card) {
        super(card);
    }

    @Override
    public MonumentalCorruption copy() {
        return new MonumentalCorruption(this);
    }
}
