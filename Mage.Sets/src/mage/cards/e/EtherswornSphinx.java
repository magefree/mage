package mage.cards.e;

import mage.MageInt;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EtherswornSphinx extends CardImpl {

    public EtherswornSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}{W}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Cascade
        this.addAbility(new CascadeAbility());
    }

    private EtherswornSphinx(final EtherswornSphinx card) {
        super(card);
    }

    @Override
    public EtherswornSphinx copy() {
        return new EtherswornSphinx(this);
    }
}
