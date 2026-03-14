package mage.cards.b;

import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.PhyrexianSaprolingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlightreaperThallid extends TransformingDoubleFacedCard {

    public BlightreaperThallid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FUNGUS}, "{1}{B}",
                "Blightsower Thallid",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.FUNGUS}, "BG");

        // Blightreaper Thallid
        this.getLeftHalfCard().setPT(2, 2);

        // {3}{G/P}: Transform Blightreaper Thallid. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{3}{G/P}")));

        // Blightsower Thallid
        this.getRightHalfCard().setPT(3, 3);

        // When this creature transforms into Blightsower Thallid or dies, create a 1/1 green Phyrexian Saproling creature token.
        this.getRightHalfCard().addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new PhyrexianSaprolingToken()), false,
                "When this creature transforms into {this} or dies, ",
                new TransformIntoSourceTriggeredAbility(null),
                new DiesSourceTriggeredAbility(null, false)
        ));
    }

    private BlightreaperThallid(final BlightreaperThallid card) {
        super(card);
    }

    @Override
    public BlightreaperThallid copy() {
        return new BlightreaperThallid(this);
    }
}
