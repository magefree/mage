package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MeldCondition;
import mage.abilities.effects.common.MeldEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GiselaTheBrokenBlade extends CardImpl {

    private static final Condition condition = new MeldCondition("Bruna, the Fading Light");

    public GiselaTheBrokenBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        this.meldsWithClazz = mage.cards.b.BrunaTheFadingLight.class;
        this.meldsToClazz = mage.cards.b.BriselaVoiceOfNightmares.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your end step, if you both own and control Gisela, the Broken Blade and a creature named Bruna, the Fading Light, exile them, then meld them into Brisela, Voice of Nightmares.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new MeldEffect(
                "Bruna, the Fading Light", "Brisela, Voice of Nightmares"
        ).setText("exile them, then meld them into Brisela, Voice of Nightmares"), TargetController.YOU, condition, false));
    }

    private GiselaTheBrokenBlade(final GiselaTheBrokenBlade card) {
        super(card);
    }

    @Override
    public GiselaTheBrokenBlade copy() {
        return new GiselaTheBrokenBlade(this);
    }
}
