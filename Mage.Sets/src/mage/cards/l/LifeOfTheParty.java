package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LifeOfTheParty extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("it's not a token");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    private static final Condition condition = new SourceMatchesFilterCondition(filter);

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
                CreaturesYouControlCount.PLURAL, StaticValue.get(0),
                Duration.EndOfTurn, "it"
        ).setText("it gets +X/+0 until end of turn, where X is the number of creatures you control"))
                .addHint(CreaturesYouControlHint.instance));

        // When Life of the Party enters the battlefield, if it's not a token, each opponent creates a token that's a copy of it. The tokens are goaded for the rest of the game.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LifeOfThePartyEffect()).withInterveningIf(condition));
    }

    private LifeOfTheParty(final LifeOfTheParty card) {
        super(card);
    }

    @Override
    public LifeOfTheParty copy() {
        return new LifeOfTheParty(this);
    }
}

class LifeOfThePartyEffect extends OneShotEffect {

    LifeOfThePartyEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent creates a token that's a copy of it. The tokens are goaded for the rest of the game";
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
        Set<Permanent> permanents = new HashSet<>();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(playerId);
            effect.setSavedPermanent(permanent);
            effect.apply(game, source);
            permanents.addAll(effect.getAddedPermanents());
        }
        if (permanents.isEmpty()) {
            return false;
        }
        game.addEffect(new GoadTargetEffect(Duration.EndOfGame)
                .setTargetPointer(new FixedTargets(permanents, game)), source);
        return true;
    }
}
