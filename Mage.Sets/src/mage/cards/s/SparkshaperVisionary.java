package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SparkshaperVisionary extends CardImpl {

    public static final FilterControlledPermanent filter =
        new FilterControlledPlaneswalkerPermanent("planeswalkers you control");

    public SparkshaperVisionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // At the beginning of combat on your turn, choose any number of target planeswalkers you control. Until end of turn, they become 3/3 blue Bird creatures with flying, hexproof, and "Whenever this creature deals combat damage to a player, scry 1."
        TriggeredAbility triggeredAbility =
            new BeginningOfCombatTriggeredAbility(
                new BecomesCreatureTargetEffect(
                    new SparkshaperVisionaryToken(),
                    false, false, Duration.EndOfTurn,
                    false, true, true
                ).setText("choose any number of target planeswalkers you control. Until end of turn, "
                    + "they become 3/3 blue Bird creatures with flying, hexproof, and "
                    + "\"Whenever this creature deals combat damage to a player, scry 1.\""
                    + " <i>(They're no longer planeswalkers. Loyalty abilities can still be activated.)</i>"),
                TargetController.YOU,
                false
            );

        triggeredAbility.addTarget(
            new TargetControlledPermanent(0, Integer.MAX_VALUE,
                filter, false
            )
        );

        this.addAbility(triggeredAbility);
    }

    private SparkshaperVisionary(final SparkshaperVisionary card) {
        super(card);
    }

    @Override
    public SparkshaperVisionary copy() {
        return new SparkshaperVisionary(this);
    }
}

class SparkshaperVisionaryToken extends TokenImpl {

    SparkshaperVisionaryToken() {
        super("", "3/3 blue Bird creatures with flying, hexproof, and "
            + "\"Whenever this creature deals combat damage to a player, scry 1.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.BIRD);
        color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HexproofAbility.getInstance());

        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
            new ScryEffect(1, false),
            false
        ));
    }

    private SparkshaperVisionaryToken(final SparkshaperVisionaryToken token) {
        super(token);
    }

    public SparkshaperVisionaryToken copy() {
        return new SparkshaperVisionaryToken(this);
    }
}
