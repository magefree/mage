package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.GoblinRogueToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MarshFlitter extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Goblin");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public MarshFlitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Marsh Flitter enters the battlefield, create two 1/1 black Goblin Rogue creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GoblinRogueToken(), 2), false));

        // Sacrifice a Goblin: Marsh Flitter has base power and toughness 3/3 until end of turn.
        Effect effect = new SetBasePowerToughnessSourceEffect(3, 3, Duration.EndOfTurn, SubLayer.SetPT_7b);
        effect.setText("{this} has base power and toughness 3/3 until end of turn");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private MarshFlitter(final MarshFlitter card) {
        super(card);
    }

    @Override
    public MarshFlitter copy() {
        return new MarshFlitter(this);
    }
}
