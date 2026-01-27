package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author North
 */
public final class Skinshifter extends CardImpl {

    public Skinshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // TODO: Either confirm it's not safe and delete this comment, or replace
        // rhinoToken with public RhinoToken. See #14315
        CreatureToken rhinoToken = new CreatureToken(
            4, 4,
            "Rhino with base power and toughness 4/4 and gains trample",
            SubType.RHINO
        ).withAbility(TrampleAbility.getInstance());

        CreatureToken birdToken = new CreatureToken(
            2, 2,
            "Bird with base power and toughness 2/2 and gains flying",
            SubType.BIRD
        ).withAbility(FlyingAbility.getInstance());

        CreatureToken plantToken = new CreatureToken(
            0, 8,
            "Plant with base power and toughness 0/8",
            SubType.PLANT
        );

        Ability ability = new SimpleActivatedAbility(
                new BecomesCreatureSourceEffect(rhinoToken, CardType.CREATURE, Duration.EndOfTurn),
                new ManaCostsImpl<>("{G}"));
        ability.getModes().setChooseText("Choose one. Activate only once each turn.");

        Mode mode = new Mode(new BecomesCreatureSourceEffect(birdToken, CardType.CREATURE, Duration.EndOfTurn));
        ability.addMode(mode);

        mode = new Mode(new BecomesCreatureSourceEffect(plantToken, CardType.CREATURE, Duration.EndOfTurn));
        ability.addMode(mode);

        this.addAbility(ability);
    }

    private Skinshifter(final Skinshifter card) {
        super(card);
    }

    @Override
    public Skinshifter copy() {
        return new Skinshifter(this);
    }
}
