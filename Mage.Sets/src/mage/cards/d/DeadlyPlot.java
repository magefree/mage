package mage.cards.d;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeadlyPlot extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("Zombie creature card from your graveyard");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public DeadlyPlot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Choose one --
        // * Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // * Return target Zombie creature card from your graveyard to the battlefield tapped.
        this.getSpellAbility().addMode(new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect(true))
                .addTarget(new TargetCardInYourGraveyard(filter)));
    }

    private DeadlyPlot(final DeadlyPlot card) {
        super(card);
    }

    @Override
    public DeadlyPlot copy() {
        return new DeadlyPlot(this);
    }
}
