package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeachersPest extends CardImpl {

    public TeachersPest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.PEST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever this creature attacks, you gain 1 life.
        this.addAbility(new AttacksTriggeredAbility(new GainLifeEffect(1)));

        // {B}{G}: Return this card from your graveyard to the battlefield tapped.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(true), new ManaCostsImpl<>("{B}{G}")
        ));
    }

    private TeachersPest(final TeachersPest card) {
        super(card);
    }

    @Override
    public TeachersPest copy() {
        return new TeachersPest(this);
    }
}
