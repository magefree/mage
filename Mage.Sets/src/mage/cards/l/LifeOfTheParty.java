package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LifeOfTheParty extends CardImpl {

    public LifeOfTheParty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Life of the Party attacks, it gets +X/+0 until end of turn, where X is the number of creatures you control.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                CreaturesYouControlCount.instance, CreaturesYouControlCount.instance, Duration.EndOfTurn
        )));

        // When Life of the Party enters the battlefield, if it's not a token, each opponent creates a token that's a copy of it. The tokens are goaded for the rest of the game.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new LifeOfThePartyEffect()), LifeOfTheParty::checkSource,
                "When {this} enters the battlefield, if it's not a token, each opponent creates a " +
                        "token that's a copy of it. The tokens are goaded for the rest of the game."
        ));
    }

    private LifeOfTheParty(final LifeOfTheParty card) {
        super(card);
    }

    @Override
    public LifeOfTheParty copy() {
        return new LifeOfTheParty(this);
    }

    static boolean checkSource(Game game, Ability source) {
        return !(source.getSourcePermanentOrLKI(game) instanceof PermanentToken);
    }
}

class LifeOfThePartyEffect extends OneShotEffect {

    LifeOfThePartyEffect() {
        super(Outcome.Benefit);
    }

    private LifeOfThePartyEffect(final LifeOfThePartyEffect effect) {
        super(effect);
    }

    @Override
    public LifeOfThePartyEffect copy() {
        return new LifeOfThePartyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentEnteredBattlefield");
        if (permanent == null) {
            return false;
        }
        List<Permanent> permanents = new ArrayList<>();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(playerId);
            effect.setSavedPermanent(permanent);
            effect.apply(game, source);
            permanents.addAll(effect.getAddedPermanents());
        }
        if (permanents.isEmpty()) {
            return false;
        }
        game.addEffect(
                new GoadTargetEffect()
                        .setDuration(Duration.EndOfGame)
                        .setTargetPointer(new FixedTargets(permanents, game)),
                source
        );
        return true;
    }
}
