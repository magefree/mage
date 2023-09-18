package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.*;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BloodToken;

/**
 *
 * @author weirddan455
 */
public final class OdricBloodCursed extends CardImpl {

    public OdricBloodCursed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Odric, Blood-Cursed enters the battlefield, create X Blood tokens, where X is the number of abilities from among flying, first strike, double strike, deathtouch, haste, hexproof, indestructible, lifelink, menace, reach, trample, and vigilance found among creatures you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OdricBloodCursedEffect()));
    }

    private OdricBloodCursed(final OdricBloodCursed card) {
        super(card);
    }

    @Override
    public OdricBloodCursed copy() {
        return new OdricBloodCursed(this);
    }
}

class OdricBloodCursedEffect extends OneShotEffect {

    public OdricBloodCursedEffect() {
        super(Outcome.Benefit);
        staticText = "create X Blood tokens, where X is the number of abilities from among flying, first strike, double strike, deathtouch, haste, hexproof, indestructible, lifelink, menace, reach, trample, and vigilance found among creatures you control";
    }

    private OdricBloodCursedEffect(final OdricBloodCursedEffect effect) {
        super(effect);
    }

    @Override
    public OdricBloodCursedEffect copy() {
        return new OdricBloodCursedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int flying = 0;
        int firstStrike = 0;
        int doubleStrike = 0;
        int deathtouch = 0;
        int haste = 0;
        int hexproof = 0;
        int indestructible = 0;
        int lifelink = 0;
        int menance = 0;
        int reach = 0;
        int trample = 0;
        int vigilance = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.isCreature(game)) {
                for (Ability ability : permanent.getAbilities(game)) {
                    if (ability instanceof FlyingAbility) {
                        flying = 1;
                    }
                    if (ability instanceof FirstStrikeAbility) {
                        firstStrike = 1;
                    }
                    if (ability instanceof DoubleStrikeAbility) {
                        doubleStrike = 1;
                    }
                    if (ability instanceof DeathtouchAbility) {
                        deathtouch = 1;
                    }
                    if (ability instanceof HasteAbility) {
                        haste = 1;
                    }
                    if (ability instanceof HexproofAbility) {
                        hexproof = 1;
                    }
                    if (ability instanceof IndestructibleAbility) {
                        indestructible = 1;
                    }
                    if (ability instanceof LifelinkAbility) {
                        lifelink = 1;
                    }
                    if (ability instanceof MenaceAbility) {
                        menance = 1;
                    }
                    if (ability instanceof ReachAbility) {
                        reach = 1;
                    }
                    if (ability instanceof TrampleAbility) {
                        trample = 1;
                    }
                    if (ability instanceof VigilanceAbility) {
                        vigilance = 1;
                    }
                }
            }
        }
        int numTokens = flying + firstStrike + doubleStrike + deathtouch + haste + hexproof + indestructible + lifelink + menance + reach + trample + vigilance;
        if (numTokens < 1) {
            return false;
        }
        return new BloodToken().putOntoBattlefield(numTokens, game, source, source.getControllerId());
    }
}
