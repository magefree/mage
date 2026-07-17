
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class HammerMage extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifacts with mana value X or less");

    static {
        filter.add(HammerMagePredicate.instance);
    }

   public HammerMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{R}, {tap}, Discard a card: Destroy all artifacts with mana value X or less.
        Ability ability = new SimpleActivatedAbility(new DestroyAllEffect(filter), new ManaCostsImpl<>("{X}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private HammerMage(final HammerMage card) {
        super(card);
    }

    @Override
    public HammerMage copy() {
        return new HammerMage(this);
    }
}

enum HammerMagePredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue() <= GetXValue.instance.calculate(game, input.getSource(), null);
    }
}
