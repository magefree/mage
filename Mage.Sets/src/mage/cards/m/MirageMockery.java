package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class MirageMockery extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("artifact creature you control");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("nonartifact creature you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter2.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public MirageMockery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Choose one —
        // • Create a token that's a copy of target artifact creature you control.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // • Create a token that's a copy of target nonartifact creature you control.
        Mode mode = new Mode(new CreateTokenCopyTargetEffect());
        mode.addTarget(new TargetPermanent(filter2));
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {2}{U}
        this.addAbility(new EntwineAbility("{2}{U}"));
    }

    private MirageMockery(final MirageMockery card) {
        super(card);
    }

    @Override
    public MirageMockery copy() {
        return new MirageMockery(this);
    }
}
