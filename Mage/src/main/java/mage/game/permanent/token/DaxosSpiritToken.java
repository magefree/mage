

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class DaxosSpiritToken extends TokenImpl {

    public DaxosSpiritToken() {
        super("Spirit Token", "white and black Spirit enchantment creature token with \"This creature's power and toughness are each equal to the number of experience counters you have.\"");
        this.setOriginalExpansionSetCode("C15");
        setTokenType(2);
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(0);
        toughness = new MageInt(0);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DaxosSpiritSetPTEffect()));
    }

    public DaxosSpiritToken(final DaxosSpiritToken token) {
        super(token);
    }

    public DaxosSpiritToken copy() {
        return new DaxosSpiritToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode().equals("C15")) {
            this.setTokenType(1);
        }
    }
}

class DaxosSpiritSetPTEffect extends ContinuousEffectImpl {

    public DaxosSpiritSetPTEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = "This creature's power and toughness are each equal to the number of experience counters you have";
    }

    public DaxosSpiritSetPTEffect(final DaxosSpiritSetPTEffect effect) {
        super(effect);
    }

    @Override
    public DaxosSpiritSetPTEffect copy() {
        return new DaxosSpiritSetPTEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null && new MageObjectReference(source.getSourceObject(game), game).refersTo(permanent, game)) {
                int amount = controller.getCounters().getCount(CounterType.EXPERIENCE);
                permanent.getPower().setValue(amount);
                permanent.getToughness().setValue(amount);
                return true;
            } else {
                discard();
            }
        }
        return false;
    }
}
