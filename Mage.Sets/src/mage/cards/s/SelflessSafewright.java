package mage.cards.s;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.MageInt;
import mage.MageObject;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class SelflessSafewright extends CardImpl {

    public SelflessSafewright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Convoke
        this.addAbility(new ConvokeAbility());

        // When this creature enters, choose a creature type. Other permanents you control of that type gain hexproof and indestructible until end of turn.
        this.addAbility(new AsEntersBattlefieldAbility(new SelflessSafewrightEffect()));
    }

    private SelflessSafewright(final SelflessSafewright card) {
        super(card);
    }

    @Override
    public SelflessSafewright copy() {
        return new SelflessSafewright(this);
    }
}

class SelflessSafewrightEffect extends OneShotEffect {

    SelflessSafewrightEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature type. Other permanents you control of that type gain hexproof and indestructible until end of turn";
    }

    private SelflessSafewrightEffect(final SelflessSafewrightEffect effect) {
        super(effect);
    }

    @Override
    public SelflessSafewrightEffect copy() {
        return new SelflessSafewrightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Choice choice = new ChoiceCreatureType(game, source);
        controller.choose(outcome, choice, game);
        SubType subType = SubType.byDescription(choice.getChoiceKey());
        if (subType == null) {
            return false;
        }
        game.informPlayers(controller.getLogName() + " chooses " + subType);
        List<Permanent> permanents = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_OTHER_CONTROLLED_PERMANENTS,
                        source.getControllerId(), source, game
                ).stream()
                .filter(permanent -> permanent.hasSubtype(subType, game))
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }

        game.addEffect(new GainAbilityTargetEffect(
                HexproofAbility.getInstance()
        ).setTargetPointer(new FixedTargets(permanents, game)), source);

        game.addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance()
        ).setTargetPointer(new FixedTargets(permanents, game)), source);

        return true;
    }
}
