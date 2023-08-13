
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author anonymous
 */
public final class SvogthosTheRestlessTomb extends CardImpl {

    public SvogthosTheRestlessTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {3}{B}{G}: Until end of turn, Svogthos, the Restless Tomb becomes a black and green Plant Zombie creature with "This creature's power and toughness are each equal to the number of creature cards in your graveyard." It's still a land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new SvogthosToken(), CardType.LAND, Duration.EndOfTurn).withDurationRuleAtStart(true), new ManaCostsImpl<>("{3}{B}{G}"));
        this.addAbility(ability);
    }

    private SvogthosTheRestlessTomb(final SvogthosTheRestlessTomb card) {
        super(card);
    }

    @Override
    public SvogthosTheRestlessTomb copy() {
        return new SvogthosTheRestlessTomb(this);
    }
}

class SvogthosToken extends TokenImpl {

    public SvogthosToken() {
        super("", "black and green Plant Zombie creature with \"This creature's power and toughness are each equal to the number of creature cards in your graveyard.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PLANT);
        subtype.add(SubType.ZOMBIE);
        color.setGreen(true);
        color.setBlack(true);
        power = new MageInt(0);
        toughness = new MageInt(0);
        CardsInControllerGraveyardCount count = new CardsInControllerGraveyardCount(new FilterCreatureCard("creature cards"));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(count, count, Duration.WhileOnBattlefield, SubLayer.SetPT_7b)));
    }
    public SvogthosToken(final SvogthosToken token) {
        super(token);
    }

    public SvogthosToken copy() {
        return new SvogthosToken(this);
    }
}
