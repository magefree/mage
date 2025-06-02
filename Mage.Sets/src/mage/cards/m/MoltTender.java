package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoltTender extends CardImpl {

    public MoltTender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Mill a card.
        this.addAbility(new SimpleActivatedAbility(new MillCardsControllerEffect(1), new TapSourceCost()));

        // {T}, Exile a card from your graveyard: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard()));
        this.addAbility(ability);
    }

    private MoltTender(final MoltTender card) {
        super(card);
    }

    @Override
    public MoltTender copy() {
        return new MoltTender(this);
    }
}
