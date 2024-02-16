package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RedWolfToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KessigWolfrider extends CardImpl {

    public KessigWolfrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // {2}{R}, {T}, Exile three cards from your graveyard: Create a 3/2 red Wolf creature token.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new RedWolfToken()), new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(3, 3, StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD)));
        this.addAbility(ability);
    }

    private KessigWolfrider(final KessigWolfrider card) {
        super(card);
    }

    @Override
    public KessigWolfrider copy() {
        return new KessigWolfrider(this);
    }
}
