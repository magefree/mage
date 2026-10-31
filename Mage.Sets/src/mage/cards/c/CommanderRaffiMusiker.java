package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author muz
 */
public final class CommanderRaffiMusiker extends CardImpl {

    public CommanderRaffiMusiker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Commander Musiker enters, choose one --
        // Exile up to one target card from a graveyard. You gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        ability.addEffect(new GainLifeEffect(2));

        // Investigate. You lose 2 life.
        Mode mode = new Mode(new InvestigateEffect());
        mode.addEffect(new LoseLifeSourceControllerEffect(2));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private CommanderRaffiMusiker(final CommanderRaffiMusiker card) {
        super(card);
    }

    @Override
    public CommanderRaffiMusiker copy() {
        return new CommanderRaffiMusiker(this);
    }
}
