package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author shieldal
 */
public final class AdunOakenshield extends CardImpl {

    public AdunOakenshield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        //{B}{R}{G}, {T}: Return target creature card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl("{B}{R}{G}"));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private AdunOakenshield(final AdunOakenshield card) {
        super(card);
    }

    @Override
    public AdunOakenshield copy() {
        return new AdunOakenshield(this);
    }
}
