
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class VastwoodAnimist extends CardImpl {

    public VastwoodAnimist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target land you control becomes an X/X Elemental creature until end of turn, where X is the number of Allies you control. It's still a land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VastwoodAnimistEffect(), new TapSourceCost());
        ability.addTarget(new TargetControlledPermanent(new FilterControlledLandPermanent()));
        this.addAbility(ability);
    }

    private VastwoodAnimist(final VastwoodAnimist card) {
        super(card);
    }

    @Override
    public VastwoodAnimist copy() {
        return new VastwoodAnimist(this);
    }
}

class VastwoodAnimistEffect extends OneShotEffect {

    static final FilterControlledPermanent filterAllies = new FilterControlledPermanent("allies you control");

    static {
        filterAllies.add(SubType.ALLY.getPredicate());
    }

    public VastwoodAnimistEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target land you control becomes an X/X Elemental creature until end of turn, where X is the number of Allies you control. It's still a land.";
    }

    private VastwoodAnimistEffect(final VastwoodAnimistEffect effect) {
        super(effect);
    }

    @Override
    public VastwoodAnimistEffect copy() {
        return new VastwoodAnimistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = new PermanentsOnBattlefieldCount(filterAllies).calculate(game, source, this);
        ContinuousEffect effect = new BecomesCreatureTargetEffect(new VastwoodAnimistElementalToken(amount), false, true, Duration.EndOfTurn);
        effect.setTargetPointer(targetPointer);
        game.addEffect(effect, source);
        return false;
    }
}

class VastwoodAnimistElementalToken extends TokenImpl {

    VastwoodAnimistElementalToken(int amount) {
        super("", "X/X Elemental creature, where X is the number of Allies you control");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(amount);
        toughness = new MageInt(amount);
    }
    private VastwoodAnimistElementalToken(final VastwoodAnimistElementalToken token) {
        super(token);
    }

    public VastwoodAnimistElementalToken copy() {
        return new VastwoodAnimistElementalToken(this);
    }
}
