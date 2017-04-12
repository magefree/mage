package mage.cards.m;

import mage.constants.ComparisonType;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.permanent.token.HippoToken2;

import java.util.UUID;

/**
 * @author Stravant
 */
public class MouthFeed extends SplitCard {
    private static final FilterControlledCreaturePermanent filterCreaturesYouControlPower3orGreater
            = new FilterControlledCreaturePermanent("creature you control with power 3 or greater.");
    static {
        filterCreaturesYouControlPower3orGreater.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public MouthFeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}","{3}{G}",false);

        // Mouth
        // Create a 3/3 green Hippo creature token
        getLeftHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new HippoToken2()));

        // to

        // Feed
        // Draw a card for each creature you control with power 3 or greater
        ((CardImpl) (getRightHalfCard())).addAbility(new AftermathAbility());
        Effect draw  = new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filterCreaturesYouControlPower3orGreater));
        getRightHalfCard().getSpellAbility().addEffect(draw);

    }

    public MouthFeed(final MouthFeed card) {
        super(card);
    }

    @Override
    public MouthFeed copy() {
        return new MouthFeed(this);
    }
}