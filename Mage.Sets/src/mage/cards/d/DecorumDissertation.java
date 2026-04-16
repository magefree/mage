package mage.cards.d;

import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.ParadigmAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DecorumDissertation extends CardImpl {

    public DecorumDissertation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        this.subtype.add(SubType.LESSON);

        // Target player draws two cards and loses 2 life.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2).withTargetDescription("and"));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Paradigm
        this.addAbility(new ParadigmAbility());
    }

    private DecorumDissertation(final DecorumDissertation card) {
        super(card);
    }

    @Override
    public DecorumDissertation copy() {
        return new DecorumDissertation(this);
    }
}
