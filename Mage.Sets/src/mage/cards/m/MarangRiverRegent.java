package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.OmenCard;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author Jmlundeen
 */
public final class MarangRiverRegent extends OmenCard {

    public MarangRiverRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{4}{U}{U}", "Coil and Catch", "{3}{U}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, return up to two other target nonland permanents to their owners' hands.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetNonlandPermanent(0, 2));
        this.addAbility(ability);

        // Coil and Catch
        // Draw three cards, then discard a card.
        this.getSpellCard().getSpellAbility().addEffect(new DrawDiscardControllerEffect(3, 1));
        this.finalizeOmen();
    }

    private MarangRiverRegent(final MarangRiverRegent card) {
        super(card);
    }

    @Override
    public MarangRiverRegent copy() {
        return new MarangRiverRegent(this);
    }
}
