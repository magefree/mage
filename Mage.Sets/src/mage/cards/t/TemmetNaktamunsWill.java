package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemmetNaktamunsWill extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ZOMBIE, "Zombies");

    public TemmetNaktamunsWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever you attack, draw a card, then discard a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1), 1
        ));

        // Whenever you draw a card, Zombies you control get +1/+1 until end of turn.
        this.addAbility(new DrawCardControllerTriggeredAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn, filter), false
        ));
    }

    private TemmetNaktamunsWill(final TemmetNaktamunsWill card) {
        super(card);
    }

    @Override
    public TemmetNaktamunsWill copy() {
        return new TemmetNaktamunsWill(this);
    }
}
