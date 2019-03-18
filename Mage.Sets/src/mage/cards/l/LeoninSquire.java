
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Plopman
 */
public final class LeoninSquire extends CardImpl {

    private static final FilterArtifactCard filter = new FilterArtifactCard("target artifact card with converted mana cost 1 or less from your graveyard");
    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 2));
    }
    
    public LeoninSquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Leonin Squire enters the battlefield, return target artifact card with converted mana cost 1 or less from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public LeoninSquire(final LeoninSquire card) {
        super(card);
    }

    @Override
    public LeoninSquire copy() {
        return new LeoninSquire(this);
    }
}
