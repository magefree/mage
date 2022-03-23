
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public final class HauntedDead extends CardImpl {

    public HauntedDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Haunted Dead enters the battlefield, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken())));

        // {1}{B}, Discard two cards: Return Haunted Dead from your graveyard to the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(true, false), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(2, new FilterCard("two cards"))));
        this.addAbility(ability);
    }

    private HauntedDead(final HauntedDead card) {
        super(card);
    }

    @Override
    public HauntedDead copy() {
        return new HauntedDead(this);
    }
}
