package mage.cards.r;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiseOfExtus extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant or sorcery card from a graveyard");

    public RiseOfExtus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W/B}{W/B}");

        // Exile target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ExileTargetEffect());

        //  Exile up to one target instant or sorcery card from a graveyard.
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(0, 1, filter));
        this.getSpellAbility().addEffect(new ExileTargetEffect().setTargetPointer(new SecondTargetPointer()));

        // Learn.
        this.getSpellAbility().addEffect(new LearnEffect().concatBy("<br>"));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);
    }

    private RiseOfExtus(final RiseOfExtus card) {
        super(card);
    }

    @Override
    public RiseOfExtus copy() {
        return new RiseOfExtus(this);
    }
}
