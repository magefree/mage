package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackedThisTurnOpponentsCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;

/**
 * @author muz
 */
public final class KurnSonOfMogh extends CardImpl {

    public KurnSonOfMogh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Battlefront -- Whenever Kurn attacks, you draw X cards and lose X life, where X is the number of opponents you attacked this turn.
        Ability ability = new AttacksTriggeredAbility(
            new DrawCardSourceControllerEffect(AttackedThisTurnOpponentsCount.instance)
                .setText("you draw X cards")
        );
        ability.addEffect(
            new LoseLifeSourceControllerEffect(AttackedThisTurnOpponentsCount.instance)
                .setText("and lose X life, where X is the number of opponents you attacked this turn")
        );
        this.addAbility(ability.withFlavorWord("Battlefront").addHint(AttackedThisTurnOpponentsCount.getHint()), new PlayersAttackedThisTurnWatcher());
    }

    private KurnSonOfMogh(final KurnSonOfMogh card) {
        super(card);
    }

    @Override
    public KurnSonOfMogh copy() {
        return new KurnSonOfMogh(this);
    }
}
