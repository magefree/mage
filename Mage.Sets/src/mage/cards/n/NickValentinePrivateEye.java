package mage.cards.n;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 * @author Cguy7777
 */
public final class NickValentinePrivateEye extends CardImpl {

    private static final FilterCreaturePermanent filterNonArtifact
            = new FilterCreaturePermanent("except by artifact creatures");
    private static final FilterControlledCreaturePermanent filterControlledArtifact
            = new FilterControlledCreaturePermanent("artifact creature you control");

    static {
        filterNonArtifact.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filterControlledArtifact.add(CardType.ARTIFACT.getPredicate());
    }

    public NickValentinePrivateEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SYNTH);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Nick Valentine, Private Eye can't be blocked except by artifact creatures.
        this.addAbility(new SimpleEvasionAbility(
                new CantBeBlockedByCreaturesSourceEffect(filterNonArtifact, Duration.WhileOnBattlefield)));

        // Whenever Nick Valentine or another artifact creature you control dies, you may investigate.
        this.addAbility(new DiesThisOrAnotherTriggeredAbility(
                new InvestigateEffect()
                        .setText("investigate. <i>(To investigate, create a Clue token. " +
                                "It's an artifact with \"{2}, Sacrifice this artifact: Draw a card.\")</i>"),
                true,
                filterControlledArtifact));
    }

    private NickValentinePrivateEye(final NickValentinePrivateEye card) {
        super(card);
    }

    @Override
    public NickValentinePrivateEye copy() {
        return new NickValentinePrivateEye(this);
    }
}
