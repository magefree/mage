
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author Plopman
 */
public final class EnsouledScimitar extends CardImpl {

    public EnsouledScimitar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // {3}: Ensouled Scimitar becomes a 1/5 Spirit artifact creature with flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new EnsouledScimitarToken(), CardType.ARTIFACT, Duration.EndOfTurn), new ManaCostsImpl<>("{3}")));
        // Equipped creature gets +1/+5.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 5)));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{2}")));
    }

    private EnsouledScimitar(final EnsouledScimitar card) {
        super(card);
    }

    @Override
    public EnsouledScimitar copy() {
        return new EnsouledScimitar(this);
    }
}

class EnsouledScimitarToken extends TokenImpl {

    public EnsouledScimitarToken() {
        super("Pincher", "1/5 Spirit artifact creature with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(1);
        toughness = new MageInt(5);
        this.addAbility(FlyingAbility.getInstance());
    }

    public EnsouledScimitarToken(final EnsouledScimitarToken token) {
        super(token);
    }

    public EnsouledScimitarToken copy() {
        return new EnsouledScimitarToken(this);
    }
}
