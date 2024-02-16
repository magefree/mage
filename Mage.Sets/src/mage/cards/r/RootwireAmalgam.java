package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.GolemXXToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RootwireAmalgam extends CardImpl {

    public RootwireAmalgam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Prototype {1}{G} -- 2/3
        this.addAbility(new PrototypeAbility(this, "{1}{G}", 2, 3));

        // {3}{G}{G}, Sacrifice Rootwire Amalgam: Create an X/X colorless Golem artifact creature token, where X is three times Rootwire Amalgam's power. It gains haste until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new RootwireAmalgamEffect(), new ManaCostsImpl<>("{3}{G}{G}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private RootwireAmalgam(final RootwireAmalgam card) {
        super(card);
    }

    @Override
    public RootwireAmalgam copy() {
        return new RootwireAmalgam(this);
    }
}

class RootwireAmalgamEffect extends OneShotEffect {

    RootwireAmalgamEffect() {
        super(Outcome.Benefit);
        staticText = "create an X/X colorless Golem artifact creature token, " +
                "where X is three times {this}'s power. It gains haste until end of turn";
    }

    private RootwireAmalgamEffect(final RootwireAmalgamEffect effect) {
        super(effect);
    }

    @Override
    public RootwireAmalgamEffect copy() {
        return new RootwireAmalgamEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = 3 * Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
        Token token = new GolemXXToken(xValue);
        token.putOntoBattlefield(1, game, source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}
