package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class PsychicSymbiont extends CardImpl {

    public PsychicSymbiont(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Psychic Symbiont enters the battlefield, target opponent discards a card and you draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1));
        ability.addEffect(new DrawCardSourceControllerEffect(1).setText("and you draw a card"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private PsychicSymbiont(final PsychicSymbiont card) {
        super(card);
    }

    @Override
    public PsychicSymbiont copy() {
        return new PsychicSymbiont(this);
    }
}
