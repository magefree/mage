package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author xenohedron
 */
public final class PrimalPlasma extends CardImpl {

    public PrimalPlasma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Primal Plasma enters the battlefield, it becomes your choice of a 3/3 creature, a 2/2 creature with flying, or a 1/6 creature with defender.
        this.addAbility(new AsEntersBattlefieldAbility(new PrimalPlasmaEffect()));
    }

    private PrimalPlasma(final PrimalPlasma card) {
        super(card);
    }

    @Override
    public PrimalPlasma copy() {
        return new PrimalPlasma(this);
    }

}

class PrimalPlasmaEffect extends OneShotEffect {

    private static final String choice33 = "a 3/3 creature";
    private static final String choice22 = "a 2/2 creature with flying";
    private static final String choice16 = "a 1/6 creature with defender";

    PrimalPlasmaEffect() {
        super(Outcome.Benefit);
        staticText = "it becomes your choice of " + choice33 + ", " + choice22 + ", or " + choice16;
    }

    private PrimalPlasmaEffect(final PrimalPlasmaEffect effect) {
        super(effect);
    }

    @Override
    public PrimalPlasmaEffect copy() {
        return new PrimalPlasmaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent == null || controller == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose what " + permanent.getIdName() + " becomes as it enters the battlefield");
        choice.getChoices().add(choice33);
        choice.getChoices().add(choice22);
        choice.getChoices().add(choice16);
        if (!controller.choose(Outcome.Neutral, choice, game)) {
            return false;
        }
        int power;
        int toughness;
        /* TODO: The chosen characteristics are copiable values; make sure this is handled correctly
         * 707.2. When copying an object, the copy acquires the copiable values of the original object's characteristics...
         * The copiable values are the values derived from the text printed on the object
         * (that text being name, mana cost, color indicator, card type, subtype, supertype, rules text, power, toughness, and/or loyalty),
         * as modified by other copy effects, by its face-down status,
         * and by "as ... enters the battlefield" and "as ... is turned face up" abilities
         * that set power and toughness (and may also set additional characteristics).
         */
        switch (choice.getChoice()) {
            case choice33:
                power = 3;
                toughness = 3;
                break;
            case choice22:
                power = 2;
                toughness = 2;
                game.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield), source);
                break;
            case choice16:
                power = 1;
                toughness = 6;
                game.addEffect(new GainAbilitySourceEffect(DefenderAbility.getInstance(), Duration.WhileOnBattlefield), source);
                break;
            default:
                return false;
        }
        game.addEffect(new SetBasePowerToughnessSourceEffect(power, toughness, Duration.WhileOnBattlefield, SubLayer.SetPT_7b), source);
        return true;
    }

}
