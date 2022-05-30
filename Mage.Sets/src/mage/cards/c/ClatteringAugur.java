package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class ClatteringAugur extends CardImpl {

    public ClatteringAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Clattering Augur can’t block.
        this.addAbility(new CantBlockAbility());

        // When Clattering Augur enters the battlefield, you draw a card and you lose 1 life.
        Effect drawEffect = new DrawCardSourceControllerEffect(1, "you");
        Ability ability = new EntersBattlefieldTriggeredAbility(drawEffect);
        Effect lifeEffect = new LoseLifeSourceControllerEffect(1);
        ability.addEffect(lifeEffect.concatBy("and"));
        this.addAbility(ability);

        // {2}{B}{B}: Return Clattering Augur from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{2}{B}{B}")));
    }

    private ClatteringAugur(final ClatteringAugur card) {
        super(card);
    }

    @Override
    public ClatteringAugur copy() {
        return new ClatteringAugur(this);
    }
}
