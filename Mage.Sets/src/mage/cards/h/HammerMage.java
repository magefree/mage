
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LoneFox
 */
public final class HammerMage extends CardImpl {

   public HammerMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{R}, {tap}, Discard a card: Destroy all artifacts with converted mana cost X or less.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HammerMageEffect(), new ManaCostsImpl<>("{X}{R}"));
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

class HammerMageEffect extends  OneShotEffect {

    public HammerMageEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy all artifacts with mana value X or less";
    }

    public HammerMageEffect(final HammerMageEffect effect) {
        super(effect);
    }

    @Override
    public HammerMageEffect copy() {
        return new HammerMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterArtifactPermanent filter = new FilterArtifactPermanent();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));
        for(Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            permanent.destroy(source, game, false);
        }
        return true;
    }
}
