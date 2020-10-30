package mage.cards.i;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.KickerWithAnyNumberModesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InscriptionOfRuin extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with converted mana cost 2 or less from your graveyard");
    private static final FilterPermanent filter2
            = new FilterCreaturePermanent("creature with converted mana cost 3 or less");

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 3));
        filter2.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public InscriptionOfRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Kicker {2}{B}{B}
        this.addAbility(new KickerWithAnyNumberModesAbility("{2}{B}{B}"));

        // Choose one. If this spell was kicked, choose any number instead.
        // • Target opponent discards two cards.
        this.getSpellAbility().getModes().setChooseText("choose one. If this spell was kicked, choose any number instead.");
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // • Return target creature card with converted mana cost 2 or less from your graveyard to the battlefield.
        Mode mode = new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addMode(mode);

        // • Destroy target creature with converted mana cost 3 or less.
        mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filter2));
        this.getSpellAbility().addMode(mode);
    }

    private InscriptionOfRuin(final InscriptionOfRuin card) {
        super(card);
    }

    @Override
    public InscriptionOfRuin copy() {
        return new InscriptionOfRuin(this);
    }
}
