package mage.cards.g;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.SwampcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GloomfangMauler extends CardImpl {

    public GloomfangMauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Swampcycling {2}
        this.addAbility(new SwampcyclingAbility(new ManaCostsImpl<>("{2}")));

        // Backup 2
        BackupAbility backupAbility = new BackupAbility(this, 2);
        this.addAbility(backupAbility);

        // Menace
        backupAbility.addAbility(new MenaceAbility(false));
    }

    private GloomfangMauler(final GloomfangMauler card) {
        super(card);
    }

    @Override
    public GloomfangMauler copy() {
        return new GloomfangMauler(this);
    }
}
