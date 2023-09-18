
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class MyrRetriever extends CardImpl {

    private static final FilterArtifactCard filter = new FilterArtifactCard("another target artifact card from your graveyard");
    static {
        filter.add(AnotherPredicate.instance);
    }

    public MyrRetriever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.MYR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Myr Retriever dies, return another target artifact card from your graveyard to your hand.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("return another target artifact card from your graveyard to your hand");
        Ability ability = new DiesSourceTriggeredAbility(effect);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private MyrRetriever(final MyrRetriever card) {
        super(card);
    }

    @Override
    public MyrRetriever copy() {
        return new MyrRetriever(this);
    }
}
