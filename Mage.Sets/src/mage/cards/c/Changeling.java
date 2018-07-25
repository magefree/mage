package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.constants.SubType;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author NinthWorld
 */
public final class Changeling extends CardImpl {

    public Changeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.subtype.add(SubType.ZERG);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Changeling can't be blocked unless defending player pays {1} for each other attacking creature.
        this.addAbility(ChangelingAbility.getInstance());

        // Whenever Changeling attacks and isn't blocked, you draw a card and lose 1 life.
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new LoseLifeSourceControllerEffect(1));
        this.addAbility(ability);
    }

    public Changeling(final Changeling card) {
        super(card);
    }

    @Override
    public Changeling copy() {
        return new Changeling(this);
    }
}
