
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class CourserOfKruphix extends CardImpl {

    public CourserOfKruphix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.CENTAUR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect()));
        // You may play the top card of your library if it's a land card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayTheTopCardEffect(new FilterLandCard())));
        // Whenever a land enters the battlefield under your control, you gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new GainLifeEffect(1), new FilterLandPermanent("a land")));
    }

    public CourserOfKruphix(final CourserOfKruphix card) {
        super(card);
    }

    @Override
    public CourserOfKruphix copy() {
        return new CourserOfKruphix(this);
    }
}
