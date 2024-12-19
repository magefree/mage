package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapVariableTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Grath
 */
public final class HazelOfTheRootbloom extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped tokens you control");
    private static final FilterPermanent filter2 = new FilterControlledPermanent("token you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(TokenPredicate.TRUE);
        filter2.add(TokenPredicate.TRUE);
    }

    public HazelOfTheRootbloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {T}, Pay 2 life, Tap X untapped tokens you control: Add X mana in any combination of colors.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaInAnyCombinationEffect(GetXValue.instance, GetXValue.instance,
                ColoredManaSymbol.W, ColoredManaSymbol.U, ColoredManaSymbol.B, ColoredManaSymbol.R, ColoredManaSymbol.G), new TapSourceCost());
        ability.addCost(new PayLifeCost(2));
        ability.addCost(new TapVariableTargetCost(filter));
        this.addAbility(ability);

        // At the beginning of your end step, create a token that's a copy of target token you control. If that token is a Squirrel, instead create two tokens that are copies of it.
        ability = new BeginningOfEndStepTriggeredAbility(
                new HazelOfTheRootbloomEffect()
        );
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private HazelOfTheRootbloom(final HazelOfTheRootbloom card) {
        super(card);
    }

    @Override
    public HazelOfTheRootbloom copy() {
        return new HazelOfTheRootbloom(this);
    }
}

class HazelOfTheRootbloomEffect extends CreateTokenCopyTargetEffect {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SQUIRREL, "Squirrel");

    HazelOfTheRootbloomEffect() {
        super();
    }

    private HazelOfTheRootbloomEffect(final HazelOfTheRootbloomEffect effect) {
        super(effect);
    }

    @Override
    public HazelOfTheRootbloomEffect copy() {
        return new HazelOfTheRootbloomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
        if (permanent != null && (filter.match(permanent, game))) {
            this.setNumber(2);
        }
        return super.apply(game, source);
    }

    @Override
    public String getText(Mode mode) {
        return super.getText(mode) + ". If that token is a Squirrel, instead create two tokens that are copies of it.";
    }
}
