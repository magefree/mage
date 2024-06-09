
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public final class GeralfsMasterpiece extends CardImpl {

    public GeralfsMasterpiece(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Geralf's Masterpiece gets -1/-1 for each card in your hand.
        DynamicValue count = new SignInversionDynamicValue(CardsInControllerHandCount.instance);
        Effect effect = new BoostSourceEffect(count, count, Duration.WhileOnBattlefield);
        effect.setText("{this} gets -1/-1 for each card in your hand");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // {3}{U}, Discard three cards: Return Geralf's Masterpiece from your graveyard to the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(true), new ManaCostsImpl<>("{3}{U}"));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(3, StaticFilters.FILTER_CARD_CARDS)));
        this.addAbility(ability);
    }

    private GeralfsMasterpiece(final GeralfsMasterpiece card) {
        super(card);
    }

    @Override
    public GeralfsMasterpiece copy() {
        return new GeralfsMasterpiece(this);
    }
}
